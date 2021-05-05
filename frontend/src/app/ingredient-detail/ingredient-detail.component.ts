import { Component, OnInit } from '@angular/core';
import { IngredientService, Ingredient, Unit } from 'gen';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
	selector: 'app-ingredient-detail',
	templateUrl: './ingredient-detail.component.html',
	styleUrls: ['./ingredient-detail.component.css']
})
export class IngredientDetailComponent implements OnInit {

	private id: number;
	ingredient?: Ingredient;
	ingredientForm: FormGroup;
	ingredientCategories: Array<string>;
	ingredientUnits: Array<string>;

	constructor(
		private route: ActivatedRoute,
		private ingredientService: IngredientService,
		private fb: FormBuilder
	) {
		this.ingredientCategories = Object.keys(Ingredient.CategoryEnum);
		this.ingredientUnits = Object.keys(Unit);
	}

	ngOnInit(): void {
		this.ingredientForm = this.fb.group({
			name: [null, Validators.required],
			category: [null, Validators.required],
			defaultUnit: [null, Validators.required],
			makroNutrients: this.fb.group({
				sugar: [null, Validators.required],
				carbohydrates: [null, Validators.required],
				saturatedFat: [null, Validators.required],
				unsaturatedFat: [null, Validators.required],
				fiber: [null, Validators.required],
				protein: [null, Validators.required],
				calories: [null, Validators.required]
			})
		});
		this.id = +this.route.snapshot.paramMap.get('id');
		if (this.id) {
			this.updateIngredient();
		}
	}

	updateIngredient(): void {
		this.ingredientService.getIngredientbyId(this.id).subscribe((ing) => {
			this.ingredient = ing;
			this.ingredientForm.patchValue(ing);
		});
	}

	onSubmit(ingredient: Ingredient): void {
		if (!this.ingredientForm.valid) {
			return;
		}
		if (this.ingredient != null) {
			ingredient.id = this.ingredient.id;
			ingredient.version = this.ingredient.version;
			this.ingredientService.putIngredientToId(this.ingredient.id, ingredient).subscribe({
				next: data => {
					console.log(data);
				},
				error: error => {
					console.error(error.error);
				}
			});
		} else {
			this.ingredientService.postNewIngredients(ingredient).subscribe({
				next: data => {
					console.log(data);
				},
				error: error => {
					console.error(error.error);
				}
			});
		}
		this.updateIngredient();
	}

	compareCategoryObjects(object1: any, object2: any) {
		return object1 && object2 && object1.id == object2.id;
	}

}
