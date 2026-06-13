import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { PublicationMetadata } from '../../../../core/models/publication-metadata.model';
import { PublicationService } from '../../../../core/services/publication.service';
import { PublicationCard } from '../../components/publication-card/publication-card';

@Component({
  selector: 'app-search',
  imports: [FormsModule, PublicationCard],
  templateUrl: './search.html',
  styleUrl: './search.scss',
})
export class Search implements OnInit {
  private readonly publicationService = inject(PublicationService);
  private readonly router = inject(Router);

  searchQuery = '';

  results = signal<PublicationMetadata[]>([]);
  viewedPublications = signal<PublicationMetadata[]>([]);

  loading = signal(false);
  historyLoading = signal(false);

  error = signal<string | null>(null);
  historyError = signal<string | null>(null);

  ngOnInit(): void {
    this.loadRecentlyViewed();
  }

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
    const workId = this.extractWorkId(openAlexId);

    this.router.navigate(['/publications', workId]);
  }

  private loadRecentlyViewed(): void {
    this.historyLoading.set(true);
    this.historyError.set(null);

    this.publicationService.getRecentlyViewed().subscribe({
      next: publications => {
        this.viewedPublications.set(publications);
        this.historyLoading.set(false);
      },
      error: () => {
        this.historyError.set('Unable to load recently viewed publications.');
        this.historyLoading.set(false);
      },
    });
  }

  private extractWorkId(openAlexId: string): string {
    return openAlexId.substring(openAlexId.lastIndexOf('/') + 1);
  }
}
