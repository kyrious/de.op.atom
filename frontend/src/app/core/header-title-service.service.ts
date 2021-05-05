import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class HeaderTitleServiceService {

	title = new BehaviorSubject<String>("Atom App");

	public getTitle(): Observable<any> {
		return this.title.asObservable();
	}

	public nextTitle(title: String): void {
		this.title.next(title);
	}
}
