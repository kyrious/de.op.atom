import { Component, OnInit } from '@angular/core';
import { Recipe, RecipePart, RecipeService, Ingredient, IngredientService } from 'gen';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { HeaderTitleServiceService } from '../core/header-title-service.service';
import { Observable, Observer, Subject } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-recipe-detail',
	templateUrl: './recipe-detail.component.html',
	styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

	private id: number;
	recipe?: Recipe;
	recipeForm: FormGroup;
	partArray: FormArray;
	ingredients: Ingredient[];
	private filteredIngredients: Subject<Ingredient[]>;
	filteredIngredientsObs: Observable<Ingredient[]>;


	constructor(private route: ActivatedRoute,
		private recipeService: RecipeService,
		private ingredientService: IngredientService,
		private fb: FormBuilder,
		private headerTiltleService: HeaderTitleServiceService) {

		this.headerTiltleService.nextTitle("RecipeDetail");
	}


	ngOnInit(): void {
		this.filteredIngredients = new Subject();
		this.filteredIngredientsObs = this.filteredIngredients.asObservable();

		this.partArray = this.fb.array([]);
		this.recipeForm = this.fb.group({
			name: [null, Validators.required],
			description: [null, Validators.required],
			parts: this.partArray
		});

		this.id = +this.route.snapshot.paramMap.get('id');
		if (this.id) {
			this.updateRecipe();
		}


		this.ingredientService.getIngredients().subscribe(ings => {
			this.ingredients = ings;
		});
	}

	private updateRecipe(): void {
		this.recipeService.getRecipebyId(this.id).subscribe((r) => {
			this.recipe = r;
			this.recipeForm.patchValue(r);
			r.parts.forEach(p => {
				this.partArray.push(this.recipePartToFormGroup(p));
			});
		});
	}

	addNewPart(): void {
		this.partArray.push(this.recipePartToFormGroup(<RecipePart>{}));
	}

	private recipePartToFormGroup(p: RecipePart): FormGroup {
		const formGroup = this.fb.group({
			id: [p.id],
			version: [p.version],
			ingredient: [p.ingredient, Validators.required],
			amount: [p.amount, Validators.required],
			unit: [p?.unit ? p?.unit : p?.ingredient?.defaultUnit]
		})
		formGroup.get('ingredient').valueChanges.pipe(
			startWith(''),
			map(value => typeof value === 'string' ? value : value.name),
			map(name => name ? this.filterIngredients(name) : this.ingredients.slice())
		).forEach(i => this.filteredIngredients.next(i));
		return formGroup;
	}

	private filterIngredients(name: string): Ingredient[] {
		const filterValue = name.toLowerCase();
		const filtered = this.ingredients.filter(ing => ing.name.toLowerCase().indexOf(filterValue) >= 0);
		return filtered;
	}

	onIngredientSelect(fc: FormControl): void {
		console.log(fc);
	}

	onSubmit(recipe: Recipe): void {
		if (!this.recipeForm.valid) {
			console.log(this.recipeForm.valid);
			return;
		}
		if (this.recipe != null) {
			recipe.id = this.recipe.id;
			recipe.version = this.recipe.version;
			this.recipeService.putRecipeToId(this.recipe.id, recipe).subscribe({
				next: data => {
					console.log(data);
				},
				error: error => {
					console.error(error.error);
				}
			});
		} else {
			this.recipeService.postNewRecipe(recipe).subscribe({
				next: data => {
					console.log(data);
				},
				error: error => {
					console.error(error.error);
				}
			});
		}
		this.updateRecipe();
	}

	displayIngredient(ing: Ingredient): string {
		if (ing) {
			return ing.name;
		}
		else {
			return '';
		}
	}
}
