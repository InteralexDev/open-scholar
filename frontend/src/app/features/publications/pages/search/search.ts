import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { PublicationMetadata } from '../../../../core/models/publication-metadata.model';
import { PublicationService } from '../../../../core/services/publication.service';

@Component({
  selector: 'app-search',
  imports: [FormsModule],
  templateUrl: './search.html',
  styleUrl: './search.scss',
})
export class Search {
  private readonly publicationService = inject(PublicationService);
  private readonly router = inject(Router);

  searchQuery = '';

  results = signal<PublicationMetadata[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);

  search(): void {
    const query = this.searchQuery.trim();

    if (!query) {
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.publicationService.search(query).subscribe({
      next: publications => {
        this.results.set(publications);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('An error occurred while searching publications.');
        this.loading.set(false);
      },
    });
  }

  openDetails(openAlexId: string): void {
    const workId = openAlexId.substring(openAlexId.lastIndexOf('/') + 1);

    this.router.navigate(['/publications', workId]);
  }
}
