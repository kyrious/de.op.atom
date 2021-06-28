import { AfterViewInit, INJECTOR, OnChanges } from '@angular/core';
import { Injector } from '@angular/core';
import { Component, forwardRef, Inject, Input, OnInit, Optional, Self } from '@angular/core';
import { AbstractControl, ControlValueAccessor, FormControl, FormControlName, FormGroup, FormGroupDirective, NgControl, NgForm, NG_VALUE_ACCESSOR } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ReplaySubject } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

export class MyErrorStateMatcher implements ErrorStateMatcher {
	isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
		const isSubmitted = form && form.submitted;
		return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
	}
}

@Component({
	selector: 'atom-filterable-select',
	templateUrl: './filterable-select.component.html',
	styleUrls: ['./filterable-select.component.css'],
	providers: [
		{
			provide: NG_VALUE_ACCESSOR,
			useExisting: forwardRef(() => FilterableSelectComponent),
			multi: true
		}
	]
})
export class FilterableSelectComponent<Type> implements OnInit, OnChanges, AfterViewInit, ControlValueAccessor {

	@Input() label: string;
	@Input() displayFunction: (filteredObject: Type) => String;
	@Input() options: Type[] = [];
	
	_options: Type[];

	selectControl: AbstractControl;
	selectSearchControl: AbstractControl;
	filter: ReplaySubject<Type[]>;

	value: Type;

	onChange: Function;
	control: any;

	matcher: ErrorStateMatcher = new MyErrorStateMatcher();

	constructor(@Inject(INJECTOR) private injector: Injector) {
		this.filter = new ReplaySubject<Type[]>(1);
	}

	ngOnInit(): void {
		this.control = this.injector.get(NgControl);
		this._options = this.options.slice();

		this.selectControl = new FormControl();
		this.selectSearchControl = new FormControl();

		this.filter.next(this._options);
		this.selectSearchControl.valueChanges.pipe(
			startWith(''),
			map(name => name ? this.doFiltering(name) : this._options?.slice())
		).forEach(i => this.filter.next(i));

		this.selectControl.valueChanges.forEach((obj) => {
			this.value = obj;
			this.onChange(this.value);
		});

		this.selectSearchControl.updateValueAndValidity();
	}

	ngOnChanges(changes) {
		console.log("ngOnChanges");
		console.log(changes)
		if (changes.options) {
			this._options = this.options.slice();
			this.filter.next(this._options);
			if(this.selectControl){
				this.selectControl.patchValue(this.value, { emitEvent: false });
			}
		}
	}

	ngAfterViewInit(): void {
		this.selectControl = this.control.control;
		this.selectControl.valueChanges.forEach((obj) => {
			this.value = obj;
			this.onChange(this.value);
		});
	}

	writeValue(obj: any): void {
		let i = this._options.findIndex(option => {option === obj});
		if (i === -1) {
			this._options.push(obj);
			this.filter.next(this._options);
		}
		this.value = obj;
		this.selectControl.patchValue(this.value, { emitEvent: false });
	}

	registerOnTouched(fn: any): void {
	}

	registerOnChange(fn: any): void {
		this.onChange = fn;
	}
	
	private doFiltering(name: string): Type[] {
		const filterValue = name.toLowerCase();
		const filtered = this._options.filter(option => this.displayFunction(option).toLowerCase().indexOf(filterValue) >= 0);
		return filtered;
	}
}
