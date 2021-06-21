import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Pipe, PipeTransform } from '@angular/core';
import { ApiModule } from '../../gen/api.module';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BASE_PATH } from 'gen';
import { IngredientDetailComponent } from './ingredient-detail/ingredient-detail.component';
import { AppRoutingModule } from './app-routing.module';
import { VersionComponent } from './version/version.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IngredientOverviewComponent } from './ingredient-overview/ingredient-overview.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';

import { HomeComponent } from './home/home.component';
import { RecipeOverviewComponent } from './recipe-overview/recipe-overview.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { FilterableSelectComponent } from './core/filterable-select/filterable-select.component';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    AppComponent,
    IngredientDetailComponent,
    VersionComponent,
    IngredientOverviewComponent,
    HomeComponent,
    RecipeOverviewComponent,
    RecipeDetailComponent,
    FilterableSelectComponent
  ],
  imports: [
	CommonModule,
    BrowserModule,
    ApiModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
	// Angular Material
	MatSidenavModule,
	MatIconModule,
	MatButtonModule,
	MatFormFieldModule,
	MatInputModule,
	MatSelectModule,
	MatAutocompleteModule,
	//Addtional Material 
	NgxMatSelectSearchModule
  ],
  providers: [{
    provide: BASE_PATH,
    useValue: 'http://localhost:4200/atom/v1'
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }