import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngredientOverviewComponent } from './ingredient-overview.component';

describe('IngredientOverviewComponent', () => {
  let component: IngredientOverviewComponent;
  let fixture: ComponentFixture<IngredientOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IngredientOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IngredientOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
