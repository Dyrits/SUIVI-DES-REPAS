# Suivi des repas

A meal tracking web application built with Java EE, developed as an ENI course project.

## About The Project

The application lets a user record meals over the days for later consultation. A form allows adding a new meal with three fields: a date, a time, and the foods (entered in a single field, separated by commas, processed server-side). Another view lists the recorded meals in chronological order, from most recent to oldest, using a single SQL query.

The database is made of two tables: REPAS, storing the date and time of each meal, and ALIMENTS, storing the name of each food (one record per food per meal).

The original assignment (in French) is available in the repository: [Module 04 - Enoncé TP 01 - Suivi des repas.pdf](Module%2004%20-%20Enonc%C3%A9%20TP%2001%20-%20Suivi%20des%20repas.pdf).

### Course

ENI | Le développement Web côté serveur - BackEnd avec Java Enterprise Edition (JEE)
Module 4 - L'accès aux données

### Built With

- Java
- Java EE

## Notes

The following notes are from the original README (translated from French):

- This project was built from scratch following specific instructions. Some elements were built inspired by a provided correction.
- The application was renamed "Suivi des repas".

## Status

Completed (V1.0, per the original README).

## License

No license file is present in this repository.
