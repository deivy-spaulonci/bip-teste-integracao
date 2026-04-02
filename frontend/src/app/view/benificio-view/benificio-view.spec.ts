import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenificioView } from './benificio-view';

describe('BenificioView', () => {
  let component: BenificioView;
  let fixture: ComponentFixture<BenificioView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BenificioView],
    }).compileComponents();

    fixture = TestBed.createComponent(BenificioView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
