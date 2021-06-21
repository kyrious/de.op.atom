import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterablSelectComponent } from './filterabl-select.component';

describe('FilterablSelectComponent', () => {
  let component: FilterablSelectComponent;
  let fixture: ComponentFixture<FilterablSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterablSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterablSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
