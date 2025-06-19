# UMM Library Management System

![Java](https://img.shields.io/badge/Java-17-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-19-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blueviolet)
![MVC](https://img.shields.io/badge/Architecture-MVC-brightgreen)
![Team](https://img.shields.io/badge/Team-3%20Members-ff69b4)

A modern library management system for Universitas Muhammadiyah Malang (UMM) with admin and student portals, built using JavaFX and PostgreSQL.

## System Architecture

[![MVC Architecture Diagram](https://drive.google.com/uc?export=view&id=11cLtgHysjicz7uve2r6_9mINMnzQXZR0)](https://drive.google.com/file/d/11cLtgHysjicz7uve2r6_9mINMnzQXZR0/view?usp=drive_link)
*Click image to view full resolution*

## 🔄 System Workflow

### Core Process Flow
[![Library System Flowchart](https://drive.google.com/uc?export=view&id=1rvOgaROhmhUf9AWLlOLgkrjF-CjtjEUb)](https://drive.google.com/file/d/1rvOgaROhmhUf9AWLlOLgkrjF-CjtjEUb/view?usp=sharing)

*Click image to view full resolution*

## Features

### 🎯 Core Functionality
- **Role-based access** (Admin & Student)
- **Complete book management** (Add/Edit/Delete)
- **Borrowing workflow** (Request→Approve→Return)
- **Real-time data tracking**

### 👨‍💻 Admin Portal
- Full book inventory control
- Borrow request management
- Student account administration
- Transaction history analytics

### 👩‍🎓 Student Portal
- Interactive book search
- Borrowing request system
- Personal history tracking
- Profile customization

## Technology Stack

| Component       | Technology                          |
|-----------------|-------------------------------------|
| Frontend        | JavaFX 19                           |
| Backend         | Java 17                             |
| Database        | PostgreSQL 15                       |
| Architecture    | MVC Pattern                         |
| ORM             | JDBC (Native SQL)                   |
| UI/UX           | JavaFX                |

## Development Team

| Role        | Contributor | Responsibilities                          |
|-------------|-------------|------------------------------------------|
| Project Lead | Zia         | System architecture, Core functionality |
| Backend Dev | Hapis       | Database design, Business logic         |
| Frontend Dev| Jecks       | UI/UX design, JavaFX implementation     |

## 1. Database Setup
## Create database (PostgreSQL)
 createdb library

## Run schema script
 psql -d library -f src/main/resources/database/schema.sql
