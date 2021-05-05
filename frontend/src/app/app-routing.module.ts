import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IngredientOverviewComponent } from './ingredient-overview/ingredient-overview.component';
import { IngredientDetailComponent } from './ingredient-detail/ingredient-detail.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { RecipeOverviewComponent } from './recipe-overview/recipe-overview.component';
import { HomeComponent } from './home/home.component';


@NgModule({
	imports: [RouterModule.forRoot([
		{ path: '', component: HomeComponent },
		{ path: 'ingredients', component: IngredientOverviewComponent },
		{ path: 'ingredient/:id', component: IngredientDetailComponent },
		{ path: 'ingredient', component: IngredientDetailComponent },
		{ path: 'recipes', component: RecipeOverviewComponent },
		{ path: 'recipe/:id', component: RecipeDetailComponent },
		{ path: 'recipe', component: RecipeDetailComponent }
	])],
	exports: [RouterModule]
})
export class AppRoutingModule { }
