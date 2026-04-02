import { TestBed } from '@angular/core/testing';

import { Benificio } from './benificio';

describe('Benificio', () => {
  let service: Benificio;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Benificio);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
