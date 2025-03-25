# Olsker Cupcakes Bestillingssystem

## Introduktion
Dette projekt er et digitalt bestillingssystem udviklet til Olsker Cupcakes, en økologisk cupcake-forretning på Bornholm. Systemet gør det muligt for kunder at oprette en konto, sammensætte deres egne cupcakes, lægge ordrer og betale online. Administratorer har mulighed for at administrere kunder, ordrer og saldoopfyldning.

## Funktioner
### **For kunder:**
- Opret og administrer en konto
- Bestil cupcakes med valgfri bund og top
- Se og rediger indkøbskurv før betaling
- Betal online via saldo på kontoen
- Se tidligere ordrer

### **For administratorer:**
- Se og administrer ordrer
- Se og administrer kunder
- Tilføj beløb til kunders saldo
- Slet ugyldige ordrer

## Teknisk Struktur
Projektet er designet med en **3. normalform (3NF) database** for at sikre optimal dataintegritet. Systemet er opdelt i følgende databasetabeller:

### **Hovedtabeller:**
- `User` (Gemmer brugere, både kunder og administratorer)
- `Customer` (Kun kunder, med balance til betaling)
- `Order` (Indeholder hovedoplysninger om ordrer)

### **Junction Tables:**
- `OrderLine` (Forbinder ordrer med specifikke cupcakes)
- `CupcakeComposition` (Forbinder bunde og toppe til færdige cupcakes)

### **Refererede tabeller:**
- `CupcakeBase` (Forskellige typer bunde)
- `CupcakeTop` (Forskellige typer toppe)

## Teknologi
- **Backend:** Java med Javalin framework
- **Frontend:** Thymeleaf, HTML, CSS
- **Database:** PostgreSQL
- **Hosting:** Azure Cloud Services

## Installation & Opsætning
1. **Klon repository:**
   ```sh
   git clone <repo-link>
   cd <project-folder>
   
2. Opsæt database:

    Opret en PostgreSQL database og kør SQL-scriptsene i setup.sql for at oprette de nødvendige tabeller og initialisere databasen.


3. Start backend. Frontend er en del af Javalin-serveren og Thymeleaf, så den kører automatisk med backend.

## Dokumentation

Alle detaljeret dokumentation om systemet findes i vores Wiki. Du kan finde Wiki-linket i projektets repository.
