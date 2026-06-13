import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PublicationMetadata } from '../../../../core/models/publication-metadata.model';
import { PublicationService } from '../../../../core/services/publication.service';

@Component({
  selector: 'app-details',
  imports: [],
  templateUrl: './details.html',
  styleUrl: './details.scss',
})
export class Details {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly publicationService = inject(PublicationService);

  readonly publication = signal<PublicationMetadata | null>(null);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);

  private readonly openAlexId: string | null;

  constructor() {
    this.openAlexId = this.route.snapshot.paramMap.get('openAlexId');

    if (!this.openAlexId) {
      this.error.set('Publication identifier is missing.');
      this.loading.set(false);
      return;
    }

    this.publicationService.getDetails(this.openAlexId).subscribe({
      next: publication => {
        this.publication.set(publication);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('An error occurred while loading publication details.');
        this.loading.set(false);
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/publications/search']);
  }

  exportJson(): void {
    if (!this.openAlexId) {
      return;
    }

    this.publicationService.exportJson(this.openAlexId).subscribe({
      next: file => this.downloadFile(file, 'publication.json'),
    });
  }

  exportDublinCore(): void {
    if (!this.openAlexId) {
      return;
    }

    this.publicationService.exportDublinCore(this.openAlexId).subscribe({
      next: file => this.downloadFile(file, 'publication-dublin-core.xml'),
    });
  }

  private downloadFile(file: Blob, filename: string): void {
    const url = window.URL.createObjectURL(file);
    const link = document.createElement('a');

    link.href = url;
    link.download = filename;
    link.click();

    window.URL.revokeObjectURL(url);
  }
}
