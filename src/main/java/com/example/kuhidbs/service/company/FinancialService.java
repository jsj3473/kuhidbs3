package com.example.kuhidbs.service.company;

import com.example.kuhidbs.dto.company.cmpinf.CCmpInfDTO;
import com.example.kuhidbs.dto.company.fnc.CFncDTO;
import com.example.kuhidbs.dto.company.fnc.RFncDTO;
import com.example.kuhidbs.entity.Fund.Employment;
import com.example.kuhidbs.entity.company.Company;
import com.example.kuhidbs.entity.company.Financial;
import com.example.kuhidbs.entity.company.Investment;
import com.example.kuhidbs.repository.Fund.EmploymentRepository;
import com.example.kuhidbs.repository.company.CompanyRepository;
import com.example.kuhidbs.repository.company.FinancialRepository;
import com.example.kuhidbs.repository.company.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialService {

    @Autowired
    private FinancialRepository financialRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private EmploymentRepository employmentRepository;

    public void saveFinancial(CCmpInfDTO CCmpInfDTO) {

        // Company 객체 조회
        Company company = companyRepository.findById(CCmpInfDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + CCmpInfDTO.getCompanyId()));
        Financial financial = Financial.builder()
                .company(company)
                .financialYear(CCmpInfDTO.getFinancialYear())
                .financialHalf(CCmpInfDTO.getFinancialHalf())
                .revenue(CCmpInfDTO.getRevenue())
                .operatingProfit(CCmpInfDTO.getOperatingProfit())
                .netIncome(CCmpInfDTO.getNetIncome())
                .totalAssets(CCmpInfDTO.getTotalAssets())
                .totalCapital(CCmpInfDTO.getTotalCapital())
                .capital(CCmpInfDTO.getCapitalFnc())
                .employeeCount(CCmpInfDTO.getEmployeeCountFnc())
                .totalDebt(CCmpInfDTO.getTotalDebt())
                .build();

        financialRepository.save(financial);
    }

    public Financial saveFinancialForCFncDTO(CFncDTO dto) {
        try {
            System.out.println("[INFO] Financial 데이터 저장 시작: " + dto);

            // 1. Company 객체 조회
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] 유효하지 않은 회사 ID: " + dto.getCompanyId());
                        return new IllegalArgumentException("Invalid company ID: " + dto.getCompanyId());
                    });
            System.out.println("[INFO] 회사 조회 성공: " + company.getCompanyName());

            // 2. Financial 데이터 생성 및 저장
            Financial financial = Financial.builder()
                    .company(company)
                    .financialYear(dto.getFinancialYear())
                    .financialHalf(dto.getFinancialHalf())
                    .revenue(dto.getRevenue())
                    .operatingProfit(dto.getOperatingProfit())
                    .netIncome(dto.getNetIncome())
                    .totalAssets(dto.getTotalAssets())
                    .totalCapital(dto.getTotalCapital())
                    .capital(dto.getCapital())
                    .employeeCount(dto.getEmployeeCount())
                    .totalDebt(dto.getTotalDebt())
                    .build();
            Financial savedFinancial = financialRepository.save(financial);
            System.out.println("[INFO] Financial 데이터 저장 완료: financialId = " + savedFinancial.getFinancialStatementId());

            // 3. 해당 회사에 속하는 모든 Investment 조회
            System.out.println("[INFO] 투자 데이터 조회 시작: companyId = " + company.getCompanyId());
            List<Investment> investmentList = investmentRepository.findByCompany_CompanyId(company.getCompanyId());
            if (investmentList.isEmpty()) {
                System.out.println("[WARN] 해당 회사의 투자 데이터가 없음: companyId = " + company.getCompanyId());
            } else {
                System.out.println("[INFO] 투자 데이터 개수: " + investmentList.size());
            }

            // 4. 각 Investment에 대해 Employment 데이터 조회 및 업데이트
            for (Investment investment : investmentList) {
                System.out.println("[INFO] 고용 데이터 조회 시작: investmentId = " + investment.getInvestmentId());

                // 기존 Employment 데이터 조회
                Employment employment = employmentRepository.findByInvestment(investment);

                if (employment != null) {
                    // 기존 데이터 업데이트
                    System.out.println("[INFO] 기존 고용 데이터 업데이트: employmentId = " + employment.getId());
                    employment.setLatestEmployeeCount(dto.getEmployeeCount()); // 최신 인력 수 업데이트
                    employment.setFinancialYear(dto.getFinancialYear()); // 기준년도 업데이트
                    employment.setFinancialHalf(dto.getFinancialHalf()); // 기준반기 업데이트
                    employmentRepository.save(employment);
                    System.out.println("[INFO] 고용 데이터 업데이트 완료: employmentId = " + employment.getId());
                } else {
                    // 데이터가 없으면 새로 생성 (실제 여길 거칠일이 있을까??)
                    System.out.println("[INFO] 새로운 고용 데이터 생성: investmentId = " + investment.getInvestmentId());
                    employment = Employment.builder()
                            .investment(investment)
                            .companyNm(company.getCompanyName()) //투자기업
                            .initialInvestmentDate(dto.getFinancialYear() + "-01-01") // 연도 기준으로 설정
                            .initialEmployeeCount(dto.getEmployeeCount()) // 최초 투자 시 인력 수
                            .latestEmployeeCount(dto.getEmployeeCount()) // 최신 인력 수
                            .finalRecoveryDate(null) // 최종 회수일자 (아직 없음)
                            .finalEmployeeCount(null) // 최종 회수 시점 인력 수 (기본값)
                            .financialYear(dto.getFinancialYear()) // 기준년도
                            .financialHalf(dto.getFinancialHalf()) // 기준반기
                            .build();

                    employmentRepository.save(employment);
                    System.out.println("[INFO] 새 고용 데이터 저장 완료: employmentId = " + employment.getId());
                }
            }


            return savedFinancial;

        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 입력 데이터 오류 발생: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            System.out.println("[ERROR] 데이터베이스 접근 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("Database error occurred", e);
        } catch (Exception e) {
            System.out.println("[ERROR] 알 수 없는 오류 발생: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

    @Transactional(readOnly = true)
    public List<RFncDTO> getRecentFinancialsByCompanyId(String companyId) {
        return financialRepository
                .findTop2ByCompany_CompanyIdOrderByFinancialYearDescFinancialHalfDesc(companyId)
                .stream()
                .map(financial -> RFncDTO.builder()
                        .financialYear(financial.getFinancialYear())
                        .financialHalf(financial.getFinancialHalf())
                        .revenue(financial.getRevenue())
                        .operatingProfit(financial.getOperatingProfit())
                        .netIncome(financial.getNetIncome())
                        .totalAssets(financial.getTotalAssets())
                        .totalCapital(financial.getTotalCapital())
                        .capital(financial.getCapital())
                        .employeeCount(financial.getEmployeeCount())
                        .totalDebt(financial.getTotalDebt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RFncDTO> getAllFinancialsByCompanyId(String companyId) {
        return financialRepository

                .findByCompany_CompanyIdOrderByFinancialYearDescFinancialHalfDesc(companyId)
                .stream()
                .map(financial -> RFncDTO.builder()
                        .financialYear(financial.getFinancialYear())
                        .financialHalf(financial.getFinancialHalf())
                        .revenue(financial.getRevenue())
                        .operatingProfit(financial.getOperatingProfit())
                        .netIncome(financial.getNetIncome())
                        .totalAssets(financial.getTotalAssets())
                        .totalCapital(financial.getTotalCapital())
                        .capital(financial.getCapital())
                        .employeeCount(financial.getEmployeeCount())
                        .totalDebt(financial.getTotalDebt())
                        .build())
                .collect(Collectors.toList());
    }

}
