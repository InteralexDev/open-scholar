import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { PublicationMetadata } from '../models/publication-metadata.model';

@Injectable({
  providedIn: 'root',
})
export class PublicationService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/publications`;
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

  exportJson(openAlexId: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${openAlexId}/export/json`, {
      responseType: 'blob',
    });
  }

  exportDublinCore(openAlexId: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${openAlexId}/export/dublin-core`, {
      responseType: 'blob',
    });
  }
}
