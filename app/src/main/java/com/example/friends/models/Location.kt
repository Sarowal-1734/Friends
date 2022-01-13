import android.os.Parcelable
import com.example.friends.models.Coordinates
import com.example.friends.models.Timezone
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (
    val street : Street,
    val city : String,
    val state : String,
    val country : String,
    val postcode : String,
    val coordinates : Coordinates,
    val timezone : Timezone
) : Parcelable