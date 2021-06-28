import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Recipe, RecipePart, RecipeService, Ingredient, IngredientService, Unit } from 'gen';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { HeaderTitleServiceService } from '../core/header-title-service.service';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';
import { Router } from '@angular/router';
import { RecipeOverviewComponent } from './../recipe-overview/recipe-overview.component';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';

@Component({
	selector: 'app-recipe-detail',
	templateUrl: './recipe-detail.component.html',
	styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

	public static ROUTE_WITH_ID = 'recipe/:id';
	public static ROUTE_WITHOUT_ID = 'recipe';

	private id: number;
	recipe?: Recipe;
	recipeForm: FormGroup;
	partArray: FormArray;
	ingredients: Ingredient[] = [];
	tags: string[] = [];

	@ViewChild('tagInput') recipeTagInput: ElementRef<HTMLInputElement>;
	separatorKeysCodes: number[] = [ENTER, COMMA];

	constructor(private route: ActivatedRoute,
		private recipeService: RecipeService,
		private ingredientService: IngredientService,
		private fb: FormBuilder,
		private headerTiltleService: HeaderTitleServiceService,
		private router: Router) {
		this.headerTiltleService.nextTitle("RecipeDetail");
	}

	ngOnInit(): void {
		this.partArray = this.fb.array([]);
		this.recipeForm = this.fb.group({
			name: [null, Validators.compose([Validators.required])],
			description: [null, Validators.compose([Validators.required])],
			tags: [null],
			parts: this.partArray
		});

		this.id = +this.route.snapshot.paramMap.get('id');
		if (this.id) {
			this.updateRecipe();
		}

		this.ingredientService.getIngredients().subscribe(ings => {
			this.ingredients = ings;
		});
		this.recipeService.getRecipeTags().subscribe(tags => {
			this.tags = tags;
		});
	}

	private updateRecipe(): void {
		this.recipeService.getRecipebyId(this.id).subscribe((r) => {
			this.recipe = r;
			this.recipeForm.patchValue(r);
			for (var i = 0; i < r.parts.length; i++) {
				this.partArray.push(this.recipePartToFormGroup(i, r.parts[i]));
			}
		});
	}

	inputNewTag(event: MatChipInputEvent): void {
		let value = (event.value || '').trim();
		if (value) {
			this.addTag(value);
		}
	}

	selectedExistingTag(event: MatAutocompleteSelectedEvent): void {
		this.addTag(event.option.viewValue);
	}

	private addTag(tag: string): void {
		this.tags.push(tag);
		// Reset input element for next user input
		this.recipeTagInput.nativeElement.value = '';
	}

	possibleTags(): string[] {
		return ["awdawdad"].slice();
	}

	removeTag(tag: string): void {
		const index = this.tags.indexOf(tag);

		if (index >= 0) {
			this.tags.splice(index, 1);
		}
	}



	addNewPart(): void {
		this.partArray.push(this.recipePartToFormGroup(this.partArray.length, <RecipePart>{}));
	}

	removePart(index: number): void {
		this.partArray.removeAt(index);
	}

	private recipePartToFormGroup(index: number, p: RecipePart): FormGroup {
		const formGroup = this.fb.group({
			id: [p.id],
			version: [p.version],
			ingredient: [p.ingredient, Validators.compose([Validators.required])],
			amount: [p.amount, Validators.compose([Validators.required])],
			unit: [p?.unit ? p?.unit : p?.ingredient?.defaultUnit, Validators.compose([Validators.required])]
		});
		formGroup.get('ingredient').valueChanges.pipe(
			filter(ing => ing !== null),
			map(ing => ing.defaultUnit)
		).subscribe(unit => {
			let unitControl = this.partArray.at(index).get('unit');
			if (!unitControl.value) {
				unitControl.patchValue(unit);
			}
		});
		return formGroup;
	}

	onSubmit(recipe: Recipe): void {
		if (!this.recipeForm.valid) {
			return;	
		}
		let callingSub: Observable<Recipe>;
		if (this.recipe != null) {
			recipe.id = this.recipe.id;
			recipe.version = this.recipe.version;
			callingSub = this.recipeService.putRecipeToId(this.recipe.id, recipe);
		} else {
			callingSub = this.recipeService.postNewRecipe(recipe);
		}

		callingSub.subscribe({
			next: data => {
				console.log(data);
				this.updateRecipe();
				/*this.router.navigateByUrl(RecipeOverviewComponent.ROUTE).then(() => {
					window.location.reload();
				});*/
			},
			error: error => {
				console.log(error);
			}
		});
	}

	compareIngredients(i1: Ingredient, i2: Ingredient): boolean {
		if (i1?.id === i2?.id) {
			return true;
		}
		return false;
	}

	displayIngredient(ing: Ingredient): String {
		return ing.name;
	}

	possibleIngredients(): Ingredient[] {
		return this.ingredients;
	}

	compareUnits(u1: Unit, u2: Unit): boolean {
		return u1 === u2;
	}

	displayUnit(u: Unit): String {
		return u;
	}

	possibleUnits(): Unit[] {
		return Object.values(Unit);
	}

}
