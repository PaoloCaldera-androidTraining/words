/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private var isLinearLayoutManager = true
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        // Sets the LinearLayoutManager of the recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LetterAdapter()

    }

    // Method to inflate the layout of the option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /* menuInflater is a variable that the framework is endowed of.
           Use it to inflate the layout of the menu in the menu object
         */
        menuInflater.inflate(R.menu.layout_menu, menu)
        return true
    }

    /* Method to describe the action to be performed when the menu item is clicked
       The clicked item of the menu is passed as argument of the function.
       Use the when() clause to perform an action, according to the specific item passed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_switch_layout -> {
                setLayout(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Set the layout of the MainActivity according to the app bar button indication
    fun setLayout(item: MenuItem){
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = GridLayoutManager(this, 3)
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
            }
            false -> {
                recyclerView.layoutManager = LinearLayoutManager(this)
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
            }
        }
        isLinearLayoutManager = !isLinearLayoutManager
    }

}
