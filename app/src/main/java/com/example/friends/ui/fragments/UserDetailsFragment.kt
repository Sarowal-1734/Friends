package com.example.friends.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.friends.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        // Getting arguments passed from UserFragment
        val args: UserDetailsFragmentArgs by navArgs()

        // Display User Info
        updateUI(args)

        // handle click event for Email
        binding.textViewEmail.setOnClickListener {
            sendEmail(args.result.email)
        }

        return binding.root
    }

    // Open mail app on click email
    private fun sendEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(Intent.createChooser(emailIntent, "title here"))
    }

    // Display User Info
    private fun updateUI(args: UserDetailsFragmentArgs) {
        binding.apply {
            // Extracting portrait from args
            val portrait = args.result.picture.large
            // Extracting full name from args
            val title = args.result.name.title
            val firstName = args.result.name.first
            val lastName = args.result.name.last
            val fullName = "$title  $firstName $lastName"
            // Extracting cell from args
            val cell = args.result.cell
            // Extracting phone from args
            val phone = args.result.phone
            // Extracting email from args
            val email = args.result.email
            // Extracting city from args
            val city = args.result.location.city
            // Extracting state from args
            val state = args.result.location.state
            // Extracting country from args
            val country = args.result.location.country

            // Display extracted data to the view
            Glide.with(this@UserDetailsFragment).load(portrait).into(imageViewPortrait)
            textViewFullName.text = fullName
            textViewCell.text = "Cell: $cell"
            textViewPhone.text = "Phone: $phone"
            textViewEmail.text = "Email: $email"
            textViewCity.text = "City: $city"
            textViewState.text = "State: $state"
            textViewCountry.text = "Country: $country"
        }
    }
}