package enarleini.finance.incomes;

import enarleini.finance.Income.IncomeRepository;
import enarleini.finance.Income.IncomeService;
import enarleini.finance.Income.Incomes;
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

class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        doNothing().when(incomeRepository).deleteById(anyLong());

        incomeService.delete(1L);

        verify(incomeRepository, times(1)).deleteById(1L);
    }


    @Test
    void testFindAllByUsername() {
        List<Incomes> incomes = Arrays.asList(new Incomes(), new Incomes());
        when(incomeRepository.findAllByUsername(anyString())).thenReturn(incomes);

        List<Incomes> result = incomeService.findAllByUsername("testuser");

        assertEquals(2, result.size());
        verify(incomeRepository, times(1)).findAllByUsername("testuser");
    }

    @Test
    void testFindById() {
        Incomes income = new Incomes();
        when(incomeRepository.findById(anyLong())).thenReturn(Optional.of(income));

        Optional<Incomes> result = incomeService.findById(1L);

        assertTrue(result.isPresent());
        verify(incomeRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        Incomes income = new Incomes();
        when(incomeRepository.save(any(Incomes.class))).thenReturn(income);

        incomeService.create(income);

        verify(incomeRepository, times(1)).save(income);
    }

    @Test
    void testSumIncomesByMonth() {
        Incomes income1 = new Incomes();
        income1.setAmount(BigDecimal.valueOf(100));
        income1.setDate(LocalDate.of(2023, 10, 1));

        Incomes income2 = new Incomes();
        income2.setAmount(BigDecimal.valueOf(200));
        income2.setDate(LocalDate.of(2023, 10, 15));

        List<Incomes> incomes = Arrays.asList(income1, income2);
        when(incomeRepository.findAllByUsername(anyString())).thenReturn(incomes);

        BigDecimal result = incomeService.sumIncomesByMonth("testuser", YearMonth.of(2023, 10));

        assertEquals(BigDecimal.valueOf(300), result);
        verify(incomeRepository, times(1)).findAllByUsername("testuser");
    }
}
