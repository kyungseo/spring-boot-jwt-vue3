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

package kyungseo.poc.framework.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author 박경서 (Kyungseo.Park@gmail.com)
 * @version 1.0
 */
@Component
@Aspect
public class LoggerAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(* kyungseo.poc..*Service.*(..))" +
	      " or execution(* kyungseo.poc..*Repository.*(..))" +
	      " or execution(* kyungseo.poc..*Mapper.*(..))")
	public void loggingPointcut() {}

	@Before("loggingPointcut()")
	public void beforeLogging(final JoinPoint joinPoint) {
	    LOGGER.debug("Before: {}", joinPoint);
		if (ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
		    LOGGER.debug("Arguments: {}",
					Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(",")));
		}
	}

	@AfterReturning(pointcut = "loggingPointcut()", returning = "result")
	public void returnLogging(final JoinPoint joinPoint, @Nullable final Object result) {
	    LOGGER.debug("Completed: {}", joinPoint);
		if (result != null) {
		    LOGGER.debug("Result: {}", result.toString());
		}
	}

}
