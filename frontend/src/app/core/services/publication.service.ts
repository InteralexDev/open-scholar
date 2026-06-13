import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { PublicationMetadata } from '../models/publication-metadata.model';

@Injectable({
  providedIn: 'root',
})
export class PublicationService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/publications';
  private readonly jsonHeaders = new HttpHeaders({
    Accept: 'application/json',
  });

  search(query: string): Observable<PublicationMetadata[]> {
    const params = new HttpParams().set('query', query);

    return this.http.get<PublicationMetadata[]>(`${this.apiUrl}/search`, {
      params,
      headers: this.jsonHeaders,
    });
  }

  getDetails(openAlexId: string): Observable<PublicationMetadata> {
    return this.http.get<PublicationMetadata>(`${this.apiUrl}/${openAlexId}`, {
      headers: this.jsonHeaders,
    });
  }

  getRecentlyViewed(): Observable<PublicationMetadata[]> {
    return this.http.get<PublicationMetadata[]>(`${this.apiUrl}/viewed`, {
      headers: this.jsonHeaders,
    });
  }
}
