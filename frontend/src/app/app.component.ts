import { Component, ChangeDetectorRef } from '@angular/core';
import { HeaderTitleServiceService } from './core/header-title-service.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent {
	title: String;
	readonly base_title = 'Atom';
	public isMenuOpen: boolean = true;

	constructor(private headerTitleService: HeaderTitleServiceService, private cdRef: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.headerTitleService.getTitle().subscribe(t => this.setTitle(t));
		this.headerTitleService.nextTitle(null);
	}

	public onSidenavClick(): void {
		this.isMenuOpen = false;
	}

	setTitle(t: String) {
		if (t) {
			this.title = this.base_title + " - " + t;
		} else {
			this.title = this.base_title;
		}
    	this.cdRef.detectChanges();
	}
}
