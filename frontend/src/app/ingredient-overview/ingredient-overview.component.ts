import { Component, OnInit } from '@angular/core';
import { IngredientService, Ingredient } from 'gen';
import { HeaderTitleServiceService } from './../core/header-title-service.service';

@Component({
	selector: 'app-ingredient-overview',
	templateUrl: './ingredient-overview.component.html',
	styleUrls: ['./ingredient-overview.component.css']
})
export class IngredientOverviewComponent implements OnInit {

	ingredients: Array<Ingredient>;

	constructor(
		private ingredientService: IngredientService,
		private headerTiltleService: HeaderTitleServiceService) { }

	ngOnInit(): void {
		this.ingredientService.getIngredients().subscribe(ingredients => this.ingredients = ingredients);
		this.headerTiltleService.nextTitle("Ingredients");
	}

}
