# $Id: numfmt.py,v 1.4 2013/10/21 06:59:21 olpa Exp $
import re
re_maybe_numfmt = re.compile('[0#.,]*[0#][0#.,]*')

def extract_number_format(s_fmt):
  # If don't know what does the format "Standard/GENERAL" mean.
  # As far as I understand, the presentation can differ depending
  # on the locale and user settings. Here is a my proposal.
  if 'GENERAL' == s_fmt:
    return (None, '#', '#')
  # Find the number-part
  m = re_maybe_numfmt.search(s_fmt)
  if m is None:
    return None                                            # return
  s_numfmt = str(m.group(0))
  # Only one comma
  pos_comma = s_numfmt.find(',')
  if pos_comma > -1:
    pos2 = s_numfmt.find(',', pos_comma+1)
    if pos2 > -1:
      return None                                          # return
  # Only one dot
  pos_dot = s_numfmt.find('.')
  if pos_dot > -1:
    pos2 = s_numfmt.find('.', pos_dot+1)
    if pos2 > -1:
      return None                                          # return
  # Exactly three positions between comma and dot
  if pos_comma > -1:
    pos2 = pos_dot if pos_dot > -1 else len(s_numfmt)
    if pos2 - pos_comma != 4:
      return None                                          # return
  # Create parts
  (part_above1000, part_below1000, part_below1) = (None, None, None)
  if pos_dot > -1:
    part_below1 = s_numfmt[pos_dot+1:]
    s_numfmt = s_numfmt[:pos_dot]
  if pos_comma > -1:
    part_above1000 = s_numfmt[:pos_comma]
    part_below1000 = s_numfmt[pos_comma+1:]
  else:
    part_below1000 = s_numfmt
  return (part_above1000, part_below1000, part_below1)

def format_number(f, a_fmt, div1000, div1):
  (part_above1000, part_below1000, part_below1) = a_fmt
  s_fmt = '%'
  if f < 0:
    is_negative = 1
    f = abs(f)
  else:
    is_negative = 0
  # Float to string with a minimal precision after comma.
  # Filling the integer part with '0' at left doesn't work for %f.
  precision  = len(part_below1) if part_below1 else 0
  s_fmt = '%.' + str(precision) + 'f'
  s_f = s_fmt % f
  # Postprocessing. Drop insignificant zeros.
  while precision:
    if '0' == part_below1[precision-1]:
      break
    if '0' != s_f[-1]:
      break
    s_f = s_f[:-1]
    precision = precision - 1
  if '.' == s_f[-1]:
    s_f = s_f[:-1]
    precision = 0
  # Add significant zeros
  part_above1 = part_above1000+part_below1000 if part_above1000 else part_below1000
  i = part_above1.find('0')
  if i > -1:
    if precision:
      need_len = len(part_above1) - i + 1 + precision
    else:
      need_len = len(part_above1) - i
    if need_len > len(s_f):
      s_f = s_f.rjust(need_len, '0')
  # Put dots and commas
  if '.' != div1:
    s_f = s_f.replace('.', div1)
  if part_above1000:
    if precision:
      div_pos = len(s_f) - precision - 4
    else:
      div_pos = len(s_f) - 3
    while div_pos > 0:
      s_f = s_f[:div_pos] + div1000 + s_f[div_pos:]
      div_pos -= 3
  # Add negative sign
  if is_negative:
    if '0' == s_f[0]:
      s_f = '-' + s_f[1:]
    else:
      s_f = '-' + s_f
  return s_f
