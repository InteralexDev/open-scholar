import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicationCard } from './publication-card';

describe('PublicationCard', () => {
  let component: PublicationCard;
  let fixture: ComponentFixture<PublicationCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicationCard],
    }).compileComponents();

    fixture = TestBed.createComponent(PublicationCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
