import { TestBed } from '@angular/core/testing';

import { HeaderTitleServiceService } from './header-title-service.service';

describe('HeaderTitleServiceService', () => {
  let service: HeaderTitleServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HeaderTitleServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
