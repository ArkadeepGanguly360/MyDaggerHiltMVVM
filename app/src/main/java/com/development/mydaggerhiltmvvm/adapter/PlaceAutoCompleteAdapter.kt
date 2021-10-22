package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.style.CharacterStyle
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.PlaceRecyclerItemLayoutBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class PlaceAutoCompleteAdapter(
    val context: Context
) :
    RecyclerView.Adapter<PlaceAutoCompleteAdapter.ViewHolder>(), Filterable {

    private val TAG = "PlacesAutoAdapter"
    private var mResultList: ArrayList<PlaceAutocomplete> = ArrayList()

    private var mContext: Context? = null
    private var STYLE_BOLD: CharacterStyle? = null
    private var STYLE_NORMAL: CharacterStyle? = null
    private var placesClient: PlacesClient? = null
    private var clickListener: ClickListener? = null

    init {
        setHasStableIds(true)
        mContext = context
        STYLE_BOLD = StyleSpan(Typeface.BOLD)
        STYLE_NORMAL = StyleSpan(Typeface.NORMAL)
        placesClient = Places.createClient(context)
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun click(place: Place)
    }

    override fun getItemId(position: Int): Long {
        return mResultList[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowBinding: PlaceRecyclerItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_recycler_item_layout,
            parent,
            false
        )
        return ViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return mResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mResultList[position]
        holder.rowBinding.placeAddress.text = item.address

        holder.rowBinding.placeAddress.setOnClickListener {
            val item = mResultList[position]

            val placeId: String = item.placeId.toString()
            val placeFields = Arrays.asList(
                com.google.android.libraries.places.api.model.Place.Field.ID,
                com.google.android.libraries.places.api.model.Place.Field.NAME,
                com.google.android.libraries.places.api.model.Place.Field.LAT_LNG,
                com.google.android.libraries.places.api.model.Place.Field.ADDRESS
            )
            val request = FetchPlaceRequest.builder(placeId, placeFields).build()
            placesClient!!.fetchPlace(request).addOnSuccessListener { response ->
                val place = response.place
                clickListener!!.click(place)
            }.addOnFailureListener { exception ->
                if (exception is ApiException) {
                    Toast.makeText(mContext, exception.message + "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class ViewHolder(val rowBinding: PlaceRecyclerItemLayoutBinding) :
        RecyclerView.ViewHolder(rowBinding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    mResultList = getPredictions(constraint)!!
                    if (mResultList != null) {
                        // The API successfully returned results.
                        results.values = mResultList
                        results.count = mResultList.size
                    }
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged()
                } else {
                    // The API did not return any results, invalidate the data set.
                    //notifyDataSetInvalidated();
                }
            }
        }
    }


    private fun getPredictions(constraint: CharSequence): ArrayList<PlaceAutocomplete>? {
        val resultList: ArrayList<PlaceAutocomplete> = ArrayList()

        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        val token = AutocompleteSessionToken.newInstance()

        //https://gist.github.com/graydon/11198540
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request =
            FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationBias(bounds)
                //.setCountry("BD")
                //.setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(constraint.toString())
                .build()
        val autocompletePredictions = placesClient!!.findAutocompletePredictions(request)

        // This method should have been called off the main UI thread. Block and wait for at most
        // 60s for a result from the API.
        try {
            Tasks.await(autocompletePredictions, 60, TimeUnit.SECONDS)
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: TimeoutException) {
            e.printStackTrace()
        }
        return if (autocompletePredictions.isSuccessful) {
            val findAutocompletePredictionsResponse = autocompletePredictions.result
            if (findAutocompletePredictionsResponse != null) for (prediction in findAutocompletePredictionsResponse.autocompletePredictions) {
                Log.i(TAG, prediction.placeId)
                resultList.add(
                    PlaceAutocomplete(
                        prediction.placeId,
                        prediction.getPrimaryText(STYLE_NORMAL).toString(),
                        prediction.getFullText(STYLE_BOLD).toString()
                    )
                )
            }
            resultList
        } else {
            resultList
        }
    }

    /**
     * Holder for Places Geo Data Autocomplete API results.
     */
    class PlaceAutocomplete internal constructor(
        var placeId: CharSequence,
        var area: CharSequence,
        var address: CharSequence
    ) {
        override fun toString(): String {
            return area.toString()
        }
    }
}

