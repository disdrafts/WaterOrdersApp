package com.example.waterordersapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterordersapp.R
import com.example.waterordersapp.databinding.ItemPurchaseBinding
import com.example.waterordersapp.domain.model.Month
import com.example.waterordersapp.domain.model.PaymentStatus

class PurchaseAdapter : ListAdapter<PurchaseUiModel, PurchaseAdapter.PurchaseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val binding = ItemPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurchaseViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class PurchaseViewHolder(private val binding: ItemPurchaseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(purchase: PurchaseUiModel) {
            val context = binding.root.context
            binding.tvClientName.text = purchase.clientName
            binding.tvMonth.text = getMonthName(
                context,
                purchase.month
            )
            binding.tvDate.text = context.getString(
                R.string.date_format,
                purchase.date
            )
            binding.tvLiters.text = context.getString(
                R.string.liters_format,
                purchase.liters
            )
            binding.tvDaysEnough.text = context.getString(
                R.string.days_enough_format,
                purchase.daysEnough
            )
            binding.tvPaymentStatus.text =
                when (purchase.paymentStatus) {
                    PaymentStatus.PAID ->
                        context.getString(R.string.paid)
                    PaymentStatus.UNPAID ->
                        context.getString(R.string.not_paid)
                }
        }
        private fun getMonthName(
            context: android.content.Context,
            month: Month
        ): String {
            return when (month) {
                Month.JANUARY ->
                    context.getString(R.string.month_january)
                Month.FEBRUARY ->
                    context.getString(R.string.month_february)
                Month.MARCH ->
                    context.getString(R.string.month_march)
                Month.APRIL ->
                    context.getString(R.string.month_april)
                Month.MAY ->
                    context.getString(R.string.month_may)
                Month.JUNE ->
                    context.getString(R.string.month_june)
                Month.JULY ->
                    context.getString(R.string.month_july)
                Month.AUGUST ->
                    context.getString(R.string.month_august)
                Month.SEPTEMBER ->
                    context.getString(R.string.month_september)
                Month.OCTOBER ->
                    context.getString(R.string.month_october)
                Month.NOVEMBER ->
                    context.getString(R.string.month_november)
                Month.DECEMBER ->
                    context.getString(R.string.month_december)
            }
        }
    }
    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<PurchaseUiModel>() {

                override fun areItemsTheSame(
                    oldItem: PurchaseUiModel,
                    newItem: PurchaseUiModel
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: PurchaseUiModel,
                    newItem: PurchaseUiModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}