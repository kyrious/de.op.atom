import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { ReplaySubject } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'atom-filterable-select',
	templateUrl: './filterable-select.component.html',
	styleUrls: ['./filterable-select.component.css']
})
export class FilterableSelectComponent<Type> implements OnInit {

	@Input() label: string;
	@Input() formControlNaming: string;
	@Input() parentFormGroup: FormGroup;
	@Input() compare: (filteredTypeObject1: Type, filteredTypeObject2: Type) => boolean;
	@Input() displayFunction: (filteredObject: Type) => String;
	@Input() options: Type[];

	selectControl: AbstractControl;
	selectSearchControl: AbstractControl;
	filter: ReplaySubject<Type[]>;
	searchTagName: string;

	private originalRawValueOfFormGroup: Function;

	constructor() {
		this.searchTagName = 'FilterableSelectComponent_' + this.formControlNaming + '_' + this.generateRandomSuffix();
		this.filter = new ReplaySubject<Type[]>(1);
	}

	ngOnInit(): void {
		this.selectSearchControl = new FormControl();
		this.parentFormGroup.addControl(this.searchTagName, this.selectSearchControl);
		this.originalRawValueOfFormGroup = this.parentFormGroup.getRawValue;
		this.parentFormGroup.getRawValue = () => {
			let original_returnVal = this.originalRawValueOfFormGroup.bind(this.parentFormGroup)();
			delete original_returnVal[this.searchTagName];
			return original_returnVal;
		};
		this.filter.next(this.options.slice());
		
		this.selectControl = this.parentFormGroup.get(this.formControlNaming);


		this.parentFormGroup.get(this.searchTagName).valueChanges.pipe(
			startWith(''),
			map(name => name ? this.doFiltering(name) : this.options?.slice())
		).forEach(i => this.filter.next(i));
		
		this.selectControl.updateValueAndValidity();
		this.selectSearchControl.updateValueAndValidity();
	}

	private doFiltering(name: string): Type[] {
		const filterValue = name.toLowerCase();
		const filtered = this.options.filter(option => this.displayFunction(option).toLowerCase().indexOf(filterValue) >= 0);
		return filtered;
	}


	private generateRandomSuffix(): String {
		return Math.random().toString(36).substring(7);
	}
}
