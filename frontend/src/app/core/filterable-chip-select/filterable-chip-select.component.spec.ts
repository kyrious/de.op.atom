import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterableChipSelectComponent } from './filterable-chip-select.component';

describe('FilterableChipSelectComponent', () => {
  let component: FilterableChipSelectComponent;
  let fixture: ComponentFixture<FilterableChipSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterableChipSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterableChipSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
