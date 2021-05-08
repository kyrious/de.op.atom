import { Component, OnInit } from '@angular/core';
import { HeaderTitleServiceService } from '../core/header-title-service.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


	constructor(
		private headerTitleService: HeaderTitleServiceService) { }

	ngOnInit(): void {
		this.headerTitleService.nextTitle("Home");
	}
}
