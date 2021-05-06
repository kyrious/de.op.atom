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
		{ path: IngredientOverviewComponent.ROUTE, component: IngredientOverviewComponent },
		{ path: IngredientDetailComponent.ROUTE_WITH_ID, component: IngredientDetailComponent },
		{ path: IngredientDetailComponent.ROUTE_WITHOUT_ID, component: IngredientDetailComponent },
		{ path: RecipeOverviewComponent.ROUTE, component: RecipeOverviewComponent },
		{ path: RecipeDetailComponent.ROUTE_WITH_ID, component: RecipeDetailComponent },
		{ path: RecipeDetailComponent.ROUTE_WITHOUT_ID, component: RecipeDetailComponent }
	])],
	exports: [RouterModule]
})
export class AppRoutingModule { }
