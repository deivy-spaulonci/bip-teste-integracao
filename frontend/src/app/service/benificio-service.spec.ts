import { TestBed } from '@angular/core/testing';

import { BenificioService } from './benificio-service';

describe('BenificioService', () => {
  let service: BenificioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BenificioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
