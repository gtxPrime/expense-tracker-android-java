package com.garvitsharma.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Form for adding one income or expense transaction. */
public class AddTransactionActivity extends AppCompatActivity {

    private MaterialButtonToggleGroup toggleType;
    private TextInputLayout layoutAmount;
    private TextInputLayout layoutDescription;
    private TextInputEditText etAmount;
    private TextInputEditText etDescription;
    private View categorySection;
    private ChipGroup chipCategories;
    private TransactionDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = new TransactionDbHelper(this);
        toggleType = findViewById(R.id.toggleType);
        layoutAmount = findViewById(R.id.layoutAmount);
        layoutDescription = findViewById(R.id.layoutDescription);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        categorySection = findViewById(R.id.categorySection);
        chipCategories = findViewById(R.id.chipCategories);

        // Categories apply to expenses only.
        toggleType.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                categorySection.setVisibility(
                        checkedId == R.id.btnExpense ? View.VISIBLE : View.GONE);
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
    }

    private void save() {
        layoutAmount.setError(null);
        layoutDescription.setError(null);

        AmountParser.Result amount = AmountParser.parse(
                etAmount.getText() == null ? "" : etAmount.getText().toString());
        String description = etDescription.getText() == null
                ? "" : etDescription.getText().toString().trim();

        boolean valid = true;
        if (!amount.isOk()) {
            valid = false;
            switch (amount.getStatus()) {
                case EMPTY:
                    layoutAmount.setError(getString(R.string.error_amount_empty));
                    break;
                case TOO_LARGE:
                    layoutAmount.setError(getString(R.string.error_amount_too_large));
                    break;
                default:
                    layoutAmount.setError(getString(R.string.error_amount_invalid));
                    break;
            }
        }
        if (description.isEmpty()) {
            valid = false;
            layoutDescription.setError(getString(R.string.error_description_empty));
        }
        if (!valid) {
            return;
        }

        boolean isExpense = toggleType.getCheckedButtonId() == R.id.btnExpense;
        TransactionType type = isExpense ? TransactionType.EXPENSE : TransactionType.INCOME;
        String category = isExpense
                ? selectedCategory()
                : getString(R.string.category_income);

        if (db.insert(type, amount.getPaise(), description, category) < 0) {
            Toast.makeText(this, R.string.error_storage, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String selectedCategory() {
        Chip chip = findViewById(chipCategories.getCheckedChipId());
        return chip == null ? getString(R.string.category_other) : chip.getText().toString();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
