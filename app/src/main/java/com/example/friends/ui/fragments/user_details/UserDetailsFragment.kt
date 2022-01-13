package com.example.friends.ui.fragments.user_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.friends.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        // Getting arguments passed from UserFragment
        val args: UserDetailsFragmentArgs by navArgs()

        // Display User Info
        updateUI(args)

        // handle click event for Email
        binding.textViewEmail.setOnClickListener(View.OnClickListener {
            sendEmail(args.results?.email)
        })

        return binding.root
    }

    // Open mail app on click email
    private fun sendEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(Intent.createChooser(emailIntent, "title here"));
    }

    // Display User Info
    private fun updateUI(args: UserDetailsFragmentArgs) {
        binding.apply {
            // Extracting portrait from args
            val portrait = args.results?.picture?.large
            // Extracting full name from args
            val title = args.results?.name?.title
            val firstName = args.results?.name?.first
            val lastName = args.results?.name?.last
            val fullName = "$title  $firstName $lastName"
            // Extracting cell from args
            val cell = args.results?.cell
            // Extracting phone from args
            val phone = args.results?.phone
            // Extracting email from args
            val email = args.results?.email
            // Extracting city from args
            val city = args.results?.location?.city
            // Extracting state from args
            val state = args.results?.location?.state
            // Extracting country from args
            val country = args.results?.location?.country

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