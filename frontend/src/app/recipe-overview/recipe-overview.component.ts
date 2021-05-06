import { Component, OnInit } from '@angular/core';
import { Recipe, RecipeService } from 'gen';
import { HeaderTitleServiceService } from './../core/header-title-service.service';

@Component({
  selector: 'app-recipe-overview',
  templateUrl: './recipe-overview.component.html',
  styleUrls: ['./recipe-overview.component.css']
})
export class RecipeOverviewComponent implements OnInit {

	public static ROUTE = 'recipes';
	
	recipes: Array<Recipe>;

	constructor(
		private recipeService: RecipeService,
		private headerTiltleService: HeaderTitleServiceService) { }

	ngOnInit(): void {
		this.recipeService.getRecipes().subscribe(r => this.recipes = r);
		this.headerTiltleService.nextTitle("Recipes");
	}
}
