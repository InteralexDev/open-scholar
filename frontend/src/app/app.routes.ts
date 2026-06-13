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
      import('./features/publications/pages/search/search.page')
        .then(m => m.SearchPage)
  },
  {
    path: 'publications/:openAlexId',
    loadComponent: () =>
      import('./features/publications/pages/details/details.page')
        .then(m => m.DetailsPage)
  },
  {
    path: '**',
    redirectTo: 'publications/search'
  }
];
