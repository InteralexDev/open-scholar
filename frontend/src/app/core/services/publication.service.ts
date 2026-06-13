import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { PublicationMetadata } from '../models/publication-metadata.model';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/publications';

  search(query: string): Observable<PublicationMetadata[]> {
    return this.http.get<PublicationMetadata[]>(`${this.apiUrl}/search`, {
      params: { query }
    });
  }

  getDetails(openAlexId: string): Observable<PublicationMetadata> {
    return this.http.get<PublicationMetadata>(`${this.apiUrl}/${encodeURIComponent(openAlexId)}`);
  }

  getViewedPublications(): Observable<PublicationMetadata[]> {
    return this.http.get<PublicationMetadata[]>(`${this.apiUrl}/viewed`);
  }
}
