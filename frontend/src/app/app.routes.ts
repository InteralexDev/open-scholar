import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'publications/search',
    pathMatch: 'full'
  },
  {
    path: 'publications/search',
    loadComponent: () =>
      import('./features/publications/pages/search/search')
        .then(m => m.Search)
  },
  {
    path: 'publications/:openAlexId',
    loadComponent: () =>
      import('./features/publications/pages/details/details')
        .then(m => m.Details)
  },
  {
    path: '**',
    redirectTo: 'publications/search'
  }
];
