import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-panel-control',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './panel-control.component.html',
  styleUrls: ['./panel-control.component.css']
})
export class PanelControlComponent implements OnInit, OnDestroy {

  rolUsuario: string = "";
  isLoggedIn: boolean = false; 
  private authSubscription!: Subscription; 

  constructor(private authService: AuthService) {}

  ngOnInit(): void {

    this.authSubscription = this.authService.loggedIn$.subscribe((isLoggedIn) => {
      this.isLoggedIn = isLoggedIn; 
      if (isLoggedIn) {
        const token = localStorage.getItem('token');
        if (token) {
          this.rolUsuario = this.authService.getRolFromToken(token);
        }
      } else {
        this.rolUsuario = "";
      }
    });
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
