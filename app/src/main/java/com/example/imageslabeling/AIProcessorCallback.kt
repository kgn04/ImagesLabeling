package com.example.imageslabeling

interface AIProcessorCallback {
    fun onLabelsReady(label: String)
    fun onTextReady(text: String)
}