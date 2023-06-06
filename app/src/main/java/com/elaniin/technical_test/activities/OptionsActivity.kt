package com.elaniin.technical_test.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    private var binding: ActivityOptionsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val name:String = intent.getStringExtra("name").toString()

        binding = ActivityOptionsBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.btnCreateTeam.setOnClickListener{
            val intent = Intent(this, PokemonActivity::class.java)
            intent.putExtra("pokedex", name)
            startActivity(intent)
        }

        binding!!.btnReviewTeams.setOnClickListener{
            val intent = Intent(this, PokemonTeamsActivity::class.java)
            intent.putExtra("pokedex", name)
            startActivity(intent)
        }

    }
}