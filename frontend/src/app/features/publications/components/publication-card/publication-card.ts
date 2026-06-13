import { Component, input, output } from '@angular/core';

import { PublicationMetadata } from '../../../../core/models/publication-metadata.model';

@Component({
  selector: 'app-publication-card',
  templateUrl: './publication-card.html',
  styleUrl: './publication-card.scss',
})
export class PublicationCard {
  publication = input.required<PublicationMetadata>();
  compact = input(false);

  selected = output<string>();

  selectPublication(): void {
    this.selected.emit(this.publication().openAlexId);
  }
}
