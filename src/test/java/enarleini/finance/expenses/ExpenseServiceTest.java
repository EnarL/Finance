package enarleini.finance.expenses;

import enarleini.finance.Expense.ExpenseRepository;
import enarleini.finance.Expense.ExpenseService;
import enarleini.finance.Expense.Expenses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        doNothing().when(expenseRepository).deleteById(anyLong());

        expenseService.delete(1L);

        verify(expenseRepository, times(1)).deleteById(1L);
    }


    @Test
    void testFindAllByUsername() {
        List<Expenses> expenses = Arrays.asList(new Expenses(), new Expenses());
        when(expenseRepository.findAllByUsername(anyString())).thenReturn(expenses);

        List<Expenses> result = expenseService.findAllByUsername("testuser");

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findAllByUsername("testuser");
    }

    @Test
    void testFindById() {
        Expenses expense = new Expenses();
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(expense));

        Optional<Expenses> result = expenseService.findById(1L);

        assertTrue(result.isPresent());
        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByUsernameAndCategory() {
        List<Expenses> expenses = Arrays.asList(new Expenses(), new Expenses());
        when(expenseRepository.findByUsernameAndCategory(anyString(), anyString())).thenReturn(expenses);

        List<Expenses> result = expenseService.findByUsernameAndCategory("testuser", "Food");

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findByUsernameAndCategory("testuser", "Food");
    }

    @Test
    void testCreate() {
        Expenses expense = new Expenses();
        when(expenseRepository.save(any(Expenses.class))).thenReturn(expense);

        expenseService.create(expense);

        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void testSumExpensesByMonth() {
        Expenses expense1 = new Expenses();
        expense1.setAmount(BigDecimal.valueOf(100));
        expense1.setDate(LocalDate.of(2023, 10, 1));

        Expenses expense2 = new Expenses();
        expense2.setAmount(BigDecimal.valueOf(200));
        expense2.setDate(LocalDate.of(2023, 10, 15));

        List<Expenses> expenses = Arrays.asList(expense1, expense2);
        when(expenseRepository.findAllByUsername(anyString())).thenReturn(expenses);

        BigDecimal result = expenseService.sumExpensesByMonth("testuser", YearMonth.of(2023, 10));

        assertEquals(BigDecimal.valueOf(300), result);
        verify(expenseRepository, times(1)).findAllByUsername("testuser");
    }

    @Test
    void testFindByUsernameAndDateBetween() {
        List<Expenses> expenses = Arrays.asList(new Expenses(), new Expenses());
        when(expenseRepository.findByUsernameAndDateBetween(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(expenses);

        List<Expenses> result = expenseService.findByUsernameAndDateBetween("testuser", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31));

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findByUsernameAndDateBetween("testuser", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31));
    }
}