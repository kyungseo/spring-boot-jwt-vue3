/* ============================================================================
 * KYUNGSEO.PoC > Development Templates for building Web Apps
 *
 * Copyright 2023 Kyungseo Park <Kyungseo.Park@gmail.com>
 * ----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================= */

package kyungseo.poc.demo.common.web.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kyungseo.poc.AppConstants;
import kyungseo.poc.framework.util.MessageSourceUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class ErrorPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private final MessageSourceUtil messageSource;

	@GetMapping("/common/error/{code}")
	public String error(final Model model, @PathVariable(required = false) final String code) {
	    LOGGER.info(">> ErrorPageController > 에러 발생!");

		model.addAttribute("title",
		        this.messageSource.getMessage("error.page.title"));
		model.addAttribute("description",
				this.messageSource.getMessage("error.page.desc." + Optional.ofNullable(code).orElseGet(() -> "500")));
		//model.addAttribute("url", request.getRequestURL());

		return AppConstants.ERROR_VIEW_NAME;
	}

}
