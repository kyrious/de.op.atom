import { Component, ElementRef, forwardRef, Input, OnInit, ViewChild } from '@angular/core';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';


@Component({
	selector: 'atom-filterable-chip-select',
	templateUrl: './filterable-chip-select.component.html',
	styleUrls: ['./filterable-chip-select.component.css'],
	providers: [
		{
			provide: NG_VALUE_ACCESSOR,
			useExisting: forwardRef(() => FilterableChipSelectComponent),
			multi: true
		}
	]
})
export class FilterableChipSelectComponent implements OnInit, ControlValueAccessor {

	separatorKeysCodes: number[] = [ENTER, COMMA];

	@Input() label: string;
	@Input() possibleTags: string[];
	
	_possibleTags: string[] = [];
	selectedElements: string[] = [];
	
	onChange: Function;

	@ViewChild('chipInput') chipInput: ElementRef<HTMLInputElement>;

	constructor() { }

	ngOnInit(): void {
		this._possibleTags = this.possibleTags.slice();
	}

	registerOnTouched(fn: any): void {
	}

	registerOnChange(fn: any): void {
		this.onChange = fn;
	}

	writeValue(obj: any): void {
		if(obj){
			this.selectedElements = obj;
		}else{
			this.selectedElements = [];
		}
	}

	selectedExistingElement(event: MatAutocompleteSelectedEvent): void {
		this.selectedElements.push(event.option.viewValue);
		this.updateFormControl();
		this.resetInputElement();
	}

	removeElementFromSelection(element: string) {
		const index = this.selectedElements.indexOf(element);
		if (index >= 0) {
			this.selectedElements.splice(index, 1);
			this.updateFormControl();
		}
	}

	inputNewElement(event: MatChipInputEvent): void {
		let value = (event.value || '').trim();
		if (value) {
			this.selectedElements.push(value);
			this._possibleTags.push(value);
			this.updateFormControl();
			this.resetInputElement();
		}
	}

	private resetInputElement(): void {
		// Reset input element for next user input
		this.chipInput.nativeElement.value = '';
	}
	
	private updateFormControl() : void{
		this.onChange(this.selectedElements);
	}
}
