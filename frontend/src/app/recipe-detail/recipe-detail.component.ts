import {Component, OnInit} from '@angular/core';
import {Recipe, RecipePart, RecipeService, Ingredient, IngredientService} from 'gen';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup, FormArray, Validators, FormControl} from '@angular/forms';
import {HeaderTitleServiceService} from '../core/header-title-service.service';
import {Observable, ReplaySubject} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {Router} from '@angular/router';
import {RecipeOverviewComponent} from '../recipe-overview/recipe-overview.component';

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
  ingredients: Ingredient[];
  filteredIngredients: ReplaySubject<Ingredient[]>[];


  constructor(private route: ActivatedRoute,
              private recipeService: RecipeService,
              private ingredientService: IngredientService,
              private fb: FormBuilder,
              private headerTitleService: HeaderTitleServiceService,
              private router: Router) {
    this.headerTitleService.nextTitle('RecipeDetail');
  }


  ngOnInit(): void {
    this.partArray = this.fb.array([]);
    this.filteredIngredients = [];
    this.recipeForm = this.fb.group({
      name: [null, Validators.compose([Validators.required])],
      description: [null, Validators.compose([Validators.required])],
      parts: this.partArray
    });

    this.id = +this.route.snapshot.paramMap.get('id');
    if (this.id) {
      this.updateRecipe();
    }

    this.ingredientService.getIngredients().subscribe(ings => {
      this.ingredients = ings;
      this.filteredIngredients.forEach(filteredIng => filteredIng.next(ings));
    });
  }

  private updateRecipe(): void {
    this.recipeService.getRecipebyId(this.id).subscribe((r) => {
      this.recipe = r;
      this.recipeForm.patchValue(r);
      for (let i = 0; i < r.parts.length; i++) {
        this.partArray.push(this.recipePartToFormGroup(i, r.parts[i]));
      }
    });
  }

  addNewPart(): void {
    this.partArray.push(this.recipePartToFormGroup(this.partArray.length, {} as RecipePart));
  }

  removePart(index: number): void {
    this.partArray.removeAt(index);
  }

  private recipePartToFormGroup(index: number, p: RecipePart): FormGroup {
    const formGroup = this.fb.group({
      id: [p.id],
      version: [p.version],
      ingredient: [p.ingredient, Validators.compose([Validators.required])],
      ingredientFilter: new FormControl(),
      amount: [p.amount, Validators.compose([Validators.required])],
      unit: [p?.unit ? p?.unit : p?.ingredient?.defaultUnit]
    });

    this.filteredIngredients[index] = new ReplaySubject<Ingredient[]>(1);
    formGroup.get('ingredientFilter').valueChanges.pipe(
      startWith(''),
      map(value => typeof value === 'string' ? value : value.name),
      map(name => name ? this.filterIngredients(name) : this.ingredients?.slice())
    ).forEach(i => this.filteredIngredients[index].next(i));
    return formGroup;
  }

  onSubmit(recipe: Recipe): void {
    if (!this.recipeForm.valid) {
      return;
    }

    // tslint:disable-next-line:no-string-literal
    recipe.parts.forEach(part => delete part['ingredientFilter']);

    let req: Observable<Recipe>;
    if (this.recipe != null) {
      recipe.id = this.recipe.id;
      recipe.version = this.recipe.version;

      req = this.recipeService.putRecipeToId(this.recipe.id, recipe);
    } else {
      req = this.recipeService.postNewRecipe(recipe);
    }

    req.subscribe(() => this.router.navigateByUrl(RecipeOverviewComponent.ROUTE),
      err => console.error(err.error));
  }

  private filterIngredients(name: string): Ingredient[] {
    const filterValue = name.toLowerCase();
    const filtered = this.ingredients.filter(ing => ing.name.toLowerCase().indexOf(filterValue) >= 0);
    return filtered;
  }

  compareIngredients(i1: Ingredient, i2: Ingredient): boolean {
    return i1?.id === i2?.id;
  }

}
