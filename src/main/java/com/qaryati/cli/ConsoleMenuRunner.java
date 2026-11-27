package com.qaryati.cli;

import com.qaryati.dto.*;
import com.qaryati.service.PopulationRecordService;
import com.qaryati.service.VillageNameService;
import com.qaryati.service.VillageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "qaryati.cli.enabled", havingValue = "true", matchIfMissing = true)
public class ConsoleMenuRunner {

    private final VillageService villageService;
    private final VillageNameService villageNameService;
    private final PopulationRecordService populationRecordService;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        Thread menuThread = new Thread(this::runMenu, "console-menu");
        menuThread.setDaemon(true);
        menuThread.start();
    }

    private void runMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=================================================");
        System.out.println("        Welcome to Qaryati System                ");
        System.out.println("=================================================");

        try {
            boolean running = true;
            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> addVillage(scanner);
                        case "2" -> viewAllVillages();
                        case "3" -> findVillageById(scanner);
                        case "4" -> deleteVillage(scanner);
                        case "5" -> addHistoricalName(scanner);
                        case "6" -> viewHistoricalNames(scanner);
                        case "7" -> addPopulationRecord(scanner);
                        case "8" -> viewPopulationRecords(scanner);
                        case "9" -> {
                            System.out.println("Exiting menu. The application is still running in the background...");
                            running = false;
                        }
                        default -> System.out.println("[!] Invalid option. Please try again.");
                    }
                } catch (NoSuchElementException e) {
                    throw e;
                } catch (Exception e) {
                    System.out.println("[X] An error occurred: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            log.info("Console input is not available, console menu stopped");
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. [+] Add a new village");
        System.out.println("2. [=] View all villages");
        System.out.println("3. [?] Find a village (by ID)");
        System.out.println("4. [x] Delete a village");
        System.out.println("5. [+] Add historical name to a village");
        System.out.println("6. [=] View historical names of a village");
        System.out.println("7. [+] Add population record to a village");
        System.out.println("8. [=] View population records of a village");
        System.out.println("9. [->] Exit menu");
        System.out.print("=> Choose an option: ");
    }

    private void addVillage(Scanner scanner) {
        System.out.println("\n-- Add New Village --");
        String nameAr = readRequired(scanner, "Enter village name (Arabic) - Required: ");
        String gov = readRequired(scanner, "Enter Governorate - Required: ");
        String nameEn = readOptional(scanner, "Enter village name (English) - Optional: ");

        VillageRequestDto request = new VillageRequestDto();
        request.setCurrentNameAr(nameAr);
        request.setGovernorate(gov);
        request.setCurrentNameEn(nameEn);

        VillageResponseDto response = villageService.createVillage(request);
        System.out.println("[v] Village added successfully! ID: " + response.getId());
    }

    private void viewAllVillages() {
        Page<VillageResponseDto> page = villageService.getAllVillages(null, null, PageRequest.of(0, 100));
        if (page.isEmpty()) {
            System.out.println("[!] No villages have been added yet.");
            return;
        }
        System.out.println("\n--- List of Villages ---");
        page.forEach(v -> System.out.println("ID: " + v.getId() + " | Name: " + v.getCurrentNameAr() + " | Governorate: " + v.getGovernorate()));
        if (page.getTotalElements() > page.getNumberOfElements()) {
            System.out.println("... showing " + page.getNumberOfElements() + " of " + page.getTotalElements());
        }
    }

    private void findVillageById(Scanner scanner) {
        Long id = readLong(scanner, "Enter village ID: ");
        VillageResponseDto v = villageService.getVillageById(id);
        System.out.println("\n--- Village Details ---");
        System.out.println("Arabic Name: " + v.getCurrentNameAr());
        System.out.println("English Name: " + (v.getCurrentNameEn() != null ? v.getCurrentNameEn() : "N/A"));
        System.out.println("Governorate: " + v.getGovernorate());
    }

    private void deleteVillage(Scanner scanner) {
        Long id = readLong(scanner, "Enter the ID of the village to delete: ");
        villageService.deleteVillage(id);
        System.out.println("[v] Village deleted successfully.");
    }

    private void addHistoricalName(Scanner scanner) {
        System.out.println("\n-- Add Historical Name --");
        Long villageId = readLong(scanner, "Enter Village ID: ");
        String name = readRequired(scanner, "Enter the historical name: ");
        String type = readOptional(scanner, "Type (FORMER, ALTERNATIVE, TRANSLITERATION): ");
        String context = readOptional(scanner, "Historical Context (Optional): ");

        VillageNameRequestDto request = new VillageNameRequestDto();
        request.setVillageId(villageId);
        request.setName(name);
        request.setNameType(type);
        request.setHistoricalContext(context);

        VillageNameResponseDto response = villageNameService.addVillageName(request);
        System.out.println("[v] Historical name added successfully! ID: " + response.getId());
    }

    private void viewHistoricalNames(Scanner scanner) {
        Long villageId = readLong(scanner, "Enter Village ID to view names: ");
        List<VillageNameResponseDto> names = villageNameService.getNamesByVillageId(villageId);

        if (names.isEmpty()) {
            System.out.println("[!] No historical names found for this village.");
            return;
        }
        System.out.println("\n--- Historical Names ---");
        names.forEach(n -> System.out.println("- Name: " + n.getName() + " | Type: " + n.getNameType() + " | Context: " + n.getHistoricalContext()));
    }

    private void addPopulationRecord(Scanner scanner) {
        System.out.println("\n-- Add Population Record --");
        Long villageId = readLong(scanner, "Enter Village ID: ");
        int value = readInt(scanner, "Enter Population Value: ");
        int year = readInt(scanner, "Enter Reference Year (e.g. 1948): ");
        String source = readRequired(scanner, "Enter Source Information (e.g. British Mandate Census): ");

        PopulationRecordRequestDto request = new PopulationRecordRequestDto();
        request.setVillageId(villageId);
        request.setPopulationValue(value);
        request.setReferenceYear(year);
        request.setSourceInformation(source);

        PopulationRecordResponseDto response = populationRecordService.addPopulationRecord(request);
        System.out.println("[v] Population record added successfully! ID: " + response.getId());
    }

    private void viewPopulationRecords(Scanner scanner) {
        Long villageId = readLong(scanner, "Enter Village ID to view population records: ");
        List<PopulationRecordResponseDto> records = populationRecordService.getRecordsByVillageId(villageId);

        if (records.isEmpty()) {
            System.out.println("[!] No population records found for this village.");
            return;
        }
        System.out.println("\n--- Population Records ---");
        records.forEach(r -> System.out.println("- Year: " + r.getReferenceYear() + " | Population: " + r.getPopulationValue() + " | Source: " + r.getSourceInformation()));
    }

    private static String readRequired(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("[!] This field is required.");
        }
    }

    private static String readOptional(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        return value.isEmpty() ? null : value;
    }

    private static Long readLong(Scanner scanner, String prompt) {
        while (true) {
            String value = readRequired(scanner, prompt);
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            String value = readRequired(scanner, prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }
}
