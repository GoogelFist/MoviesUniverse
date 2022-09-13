package com.example.moviesuniverse.presentation.screens

interface EventHandler<E> {

    fun obtainEvent(event: E)
}