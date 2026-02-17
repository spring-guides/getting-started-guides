#!/usr/bin/env bash
set -euo pipefail

trap 'echo ""; exit 130' INT

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

RUN_INITIAL=false
RUN_COMPLETE=false
RUN_MAVEN=false
RUN_GRADLE=false

set_both_build_systems() {
  RUN_MAVEN=true
  RUN_GRADLE=true
}

set_both_stages() {
  RUN_INITIAL=true
  RUN_COMPLETE=true
}

parse_arg() {
  case "$1" in
    initial)  RUN_INITIAL=true ;;
    complete) RUN_COMPLETE=true ;;
    maven)    RUN_MAVEN=true ;;
    gradle)   RUN_GRADLE=true ;;
    *)        return 1 ;;
  esac
  return 0
}

case "${#@}" in
  0)
    RUN_INITIAL=true
    RUN_COMPLETE=true
    RUN_MAVEN=true
    RUN_GRADLE=true
    ;;
  1)
    case "${1:-}" in
      initial)
        RUN_INITIAL=true
        set_both_build_systems
        ;;
      complete)
        RUN_COMPLETE=true
        set_both_build_systems
        ;;
      maven)
        set_both_stages
        RUN_MAVEN=true
        ;;
      gradle)
        set_both_stages
        RUN_GRADLE=true
        ;;
      *)
        echo "Usage: $0 [initial|complete|maven|gradle] [initial|complete|maven|gradle]" >&2
        echo "  No argument: run initial + complete (Maven + Gradle each)" >&2
        echo "  initial:     run initial only (Maven + Gradle)" >&2
        echo "  complete:    run complete only (Maven + Gradle)" >&2
        echo "  maven:       run Maven only (initial + complete)" >&2
        echo "  gradle:      run Gradle only (initial + complete)" >&2
        echo "  Two args:    e.g. 'maven initial', 'initial gradle', 'gradle complete'" >&2
        exit 1
        ;;
    esac
    ;;
  2)
    if parse_arg "$1" && parse_arg "$2"; then
      if { $RUN_INITIAL || $RUN_COMPLETE; } && { $RUN_MAVEN || $RUN_GRADLE; }; then
        : # valid combination
      else
        echo "Usage: $0 [initial|complete|maven|gradle] [initial|complete|maven|gradle]" >&2
        echo "  Two args must be one of (initial|complete) and one of (maven|gradle)" >&2
        exit 1
      fi
    else
      echo "Usage: $0 [initial|complete|maven|gradle] [initial|complete|maven|gradle]" >&2
      echo "  Arguments must be from: initial, complete, maven, gradle" >&2
      exit 1
    fi
    ;;
  *)
    echo "Usage: $0 [initial|complete|maven|gradle] [initial|complete|maven|gradle]" >&2
    exit 1
    ;;
esac

PASSED=()
FAILED=()

run_build() {
  local label="$1"
  local dir="$2"
  local cmd="$3"
  echo "========================================"
  echo "Running: $label"
  echo "  dir: $dir"
  echo "  cmd: $cmd"
  echo "========================================"
  if (cd "$dir" && eval "$cmd"); then
    PASSED+=("$label")
    echo "[PASS] $label"
  else
    FAILED+=("$label")
    echo "[FAIL] $label"
  fi
  echo ""
}

if "$RUN_INITIAL"; then
  if "$RUN_MAVEN"; then
    run_build "initial + Maven" "initial" "./mvnw -q clean verify"
  fi
  if "$RUN_GRADLE"; then
    run_build "initial + Gradle" "initial" "./gradlew -q clean build"
  fi
fi

if "$RUN_COMPLETE"; then
  if "$RUN_MAVEN"; then
    run_build "complete + Maven" "complete" "./mvnw -q clean verify"
  fi
  if "$RUN_GRADLE"; then
    run_build "complete + Gradle" "complete" "./gradlew -q clean build"
  fi
fi

echo "========================================"
echo "Summary"
echo "========================================"
if ((${#PASSED[@]} > 0)); then
  echo "Passed (${#PASSED[@]}):"
  printf '  - %s\n' "${PASSED[@]}"
fi
if ((${#FAILED[@]} > 0)); then
  echo "Failed (${#FAILED[@]}):"
  printf '  - %s\n' "${FAILED[@]}"
  exit 1
fi
echo "All builds passed."
exit 0
