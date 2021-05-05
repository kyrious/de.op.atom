import { Component, OnInit } from '@angular/core';
import { HeaderTitleServiceService } from './../core/header-title-service.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


	constructor(
		private headerTiltleService: HeaderTitleServiceService) { }

	ngOnInit(): void {
		this.headerTiltleService.nextTitle("Home");
	}
}
