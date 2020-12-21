package ro.pub.acs.pollutionchecker.city_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ro.pub.acs.pollutionchecker.R
import ro.pub.acs.pollutionchecker.database.HistoryDatabase
import ro.pub.acs.pollutionchecker.database.SearchedCity
import ro.pub.acs.pollutionchecker.databinding.FragmentCityInfoBinding
import ro.pub.acs.pollutionchecker.utils.Helper


class CityInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCityInfoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_city_info, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = CityInfoFragmentArgs.fromBundle(requireArguments())
        val dataSource = HistoryDatabase.getInstance(application).historyDatabaseDao
        val viewModelFactory = CityInfoViewModelFactory(dataSource)

        val cityInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CityInfoViewModel::class.java)

        binding.cityInfoViewModel = cityInfoViewModel

        val navController = findNavController()
        binding.navController = navController

        cityInfoViewModel.navigateToHistory.observe(viewLifecycleOwner, {
            if (it == true) {
                navController.navigate(
                    CityInfoFragmentDirections.actionCityInfoFragmentToHistoryFragment()
                )
                cityInfoViewModel.doneNavigating()
            }
        })



        if (arguments.searchId >= 0) {
            cityInfoViewModel.retrieveCityInfo(arguments.searchId)
            cityInfoViewModel.searchedCity?.observe(viewLifecycleOwner, { searchedCity ->
                if (searchedCity != null) {
                    val (imageId, colorId, textId) = Helper.getRating(searchedCity.aqi)
                    binding.image.setImageResource(imageId)
                    binding.aqi.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            colorId
                        )
                    )
                    binding.rating.setText(textId)
                    binding.searchedCity = searchedCity
                }
                cityInfoViewModel.resetCityInfo()
            })
        } else {
            val searchedCity = SearchedCity(
                city = arguments.city, region = arguments.region,
                country = arguments.country, aqi = arguments.aqi,
                pm25 = arguments.pm25, pm10 = arguments.pm10, o3 = arguments.o3,
                no2 = arguments.no2, so2 = arguments.so2, co = arguments.co
            )

            cityInfoViewModel.insertCity(searchedCity)

            val (imageId, colorId, textId) = Helper.getRating(searchedCity.aqi)
            binding.image.setImageResource(imageId)
            binding.aqi.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
            binding.rating.setText(textId)
            binding.searchedCity = searchedCity
        }

        binding.directionsHistory = CityInfoFragmentDirections.actionCityInfoFragmentToHistoryFragment()
        return binding.root
    }
}